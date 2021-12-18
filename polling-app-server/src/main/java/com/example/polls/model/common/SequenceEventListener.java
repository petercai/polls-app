package com.example.polls.model.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Id;
import org.springframework.data.mapping.MappingException;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.Collection;

@Component
public class SequenceEventListener extends AbstractMongoEventListener<IMongoModel> {

    @Autowired
    SequenceGenerator generator;
    @Autowired
    MongoOperations operations;

    @Override
    public void onBeforeConvert(BeforeConvertEvent<IMongoModel> event) {
        IMongoModel model = event.getSource();
        /*
         * auto-populate ID field
         */
        Long id = model.getId();
        if (id == null || id < 1) {
            model.setId(generator.generate(model.getSequenceName()));
        }

        /*
         * Cascade Save
         */
        ReflectionUtils.doWithFields(model.getClass(),
                                     new ReflectionUtils.FieldCallback() {

                                         @Override
                                         public void doWith(Field field) throws IllegalArgumentException, IllegalAccessException {
                                             ReflectionUtils.makeAccessible(field);

                                             if (field.isAnnotationPresent(DBRef.class) && field.isAnnotationPresent(CascadeSave.class)) {
                                                 Object fieldValue = field.get(model);
                                                 if (fieldValue instanceof Collection) {
                                                     // TODO: batch saving
                                                     for (Object single : (Collection) fieldValue) {
                                                         saveSingleObject(single);
                                                     }
                                                 } else {
                                                     saveSingleObject(fieldValue);
                                                 }
                                             }
                                         }

                                         private void saveSingleObject(Object object) {
                                             DbRefFieldCallback callback = new DbRefFieldCallback();
                                             ReflectionUtils.doWithFields(object.getClass(),
                                                                          callback);
                                             if (!callback.isIdFound()) {
                                                 throw new MappingException(String.format("Cannot perform cascade save on child object(%s) without ID set",
                                                                                          model.getClass().getName()));
                                             }
                                             operations.save(object);

                                         }
                                     });

    }

    private static class DbRefFieldCallback implements ReflectionUtils.FieldCallback {
        private boolean idFound;

        @Override
        public void doWith(Field field) throws IllegalArgumentException, IllegalAccessException {
            ReflectionUtils.makeAccessible(field);
            if (field.isAnnotationPresent(Id.class)) {
                idFound = true;
            }
        }

        public boolean isIdFound() {
            return idFound;
        }
    }
}
