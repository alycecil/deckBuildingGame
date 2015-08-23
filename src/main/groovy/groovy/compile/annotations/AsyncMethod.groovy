package groovy.compile.annotations

import org.codehaus.groovy.transform.GroovyASTTransformationClass
import java.lang.annotation.*

/**
 * User: felipeg
 * Date: 9/6/11
 * Time: 10:48 AM
 */
@Retention (RetentionPolicy.SOURCE)
@Target ([ElementType.METHOD])
@GroovyASTTransformationClass (["groovy.compile.transformation.AsyncTransformation"])
public @interface AsyncMethod { }