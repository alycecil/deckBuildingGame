package groovy.compile.transformation;
import groovy.compile.annotations.AsyncMethod
import groovy.compile.annotations.Asynchronous

import org.codehaus.groovy.ast.*
import org.codehaus.groovy.ast.builder.AstBuilder
import org.codehaus.groovy.control.CompilePhase
import org.codehaus.groovy.control.SourceUnit
import org.codehaus.groovy.transform.*

/**
 * User: felipeg
 * Date: 9/6/11
 * Time: 10:48 AM
 */
@GroovyASTTransformation(phase = CompilePhase.SEMANTIC_ANALYSIS) //CompilePhase.SEMANTIC_ANALYSIS
class AsyncTransformation implements ASTTransformation{

    void visit(ASTNode[] astNodes, SourceUnit sourceUnit) {


        if (!astNodes ) return
        if (!astNodes[0] || !astNodes[1]) return
        if (!(astNodes[0] instanceof AnnotationNode)) return
        if (astNodes[0].classNode?.name != Asynchronous.class.name) return

        def methods = makeMethods(astNodes[1])
        if(methods){
            astNodes[1]?.interfaces = [  ClassHelper.make(GroovyInterceptable, false), ] as ClassNode []
            astNodes[1]?.addMethod(methods?.find { it.name == 'invokeMethod' })
        }
    }

    def makeMethods(ClassNode source){
         def methods = source.methods
         def annotatedMethods = methods.findAll {  it?.annotations?.findAll { it?.classNode?.name == AsyncMethod.class.name } }

         if(annotatedMethods){

             def expression = annotatedMethods.collect { "name == \"${it.name}\"" }.join(" || ")

             def ast = new AstBuilder().buildFromString(CompilePhase.INSTRUCTION_SELECTION, false, """
                package groovy.helper.compile;{source.packageName}
                class ${source.nameWithoutPackage} implements GroovyInterceptable {
                    def invokeMethod(String name, Object args){
                        if(${expression}){
                            Thread.start{
                                def calledMethod = ${source.nameWithoutPackage}.metaClass.getMetaMethod(name, args)
                                calledMethod?.invoke(this, args)
                            }
                        }else{
                           def calledMethod = ${source.nameWithoutPackage}.metaClass.getMetaMethod(name, args)?.invoke(this,args)
                        }
                    }
                }
             """)

             ast[1].methods
         }
    }

}