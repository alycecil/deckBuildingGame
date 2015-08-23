package groovy.compile.annotations;

import java.lang.annotation.*

import org.codehaus.groovy.transform.GroovyASTTransformationClass

/**
 * User: felipeg
 * Date: 9/6/11
 * Time: 10:48 AM
 */
@Retention (RetentionPolicy.SOURCE)
@Target ([ElementType.TYPE])
@GroovyASTTransformationClass (["groovy.compile.transformation.AsyncTransformation"])
public @interface Asynchronous {

}

