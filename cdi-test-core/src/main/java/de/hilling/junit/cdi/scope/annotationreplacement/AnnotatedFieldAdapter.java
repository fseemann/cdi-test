package de.hilling.junit.cdi.scope.annotationreplacement;

import javax.enterprise.inject.spi.AnnotatedField;
import javax.enterprise.inject.spi.AnnotatedMember;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Map;

public class AnnotatedFieldAdapter<T>
    extends AnnotatedMemberAdapter<T> implements AnnotatedField<T> {

    AnnotatedFieldAdapter(AnnotatedMember<? super T> member, Map<Class<? extends Annotation>, Annotation> replacementMap) {
        super(member, replacementMap);
    }

    public Field getJavaMember() {
        return (Field) super.getJavaMember();
    }


}
