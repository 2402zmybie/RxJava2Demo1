package com.hr.annotation_compiler;

import com.google.auto.service.AutoService;
import com.hr.annotation.BindPath;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.tools.JavaFileObject;
import java.io.IOException;
import java.io.Writer;
import java.util.*;

@AutoService(Process.class)
public class AnnotationCompiler extends AbstractProcessor {

    private Filer filer;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        filer = processingEnv.getFiler();
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return processingEnv.getSourceVersion();
    }


    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> types = new HashSet<>();
        types.add(BindPath.class.getCanonicalName());
        return types;
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        Set<? extends Element> elementsAnnotatedWith = roundEnv.getElementsAnnotatedWith(BindPath.class);
        Map<String, String> map = new HashMap<>();
        for (Element element : elementsAnnotatedWith) {
            //类节点
            TypeElement typeElement = (TypeElement) element;
            //获取到带包名的类名 (value)
            String activityName = typeElement.getQualifiedName().toString();
            BindPath annotation = typeElement.getAnnotation(BindPath.class);
            String key = annotation.value();
            map.put(key, activityName + ".class");
        }
        //如果当前模块没有标记注解, 那么不执行下面的代码
        if(map.size() > 0) {
            Writer writer = null;
            String className = "ActivityUtil" + System.currentTimeMillis();
            try {
                JavaFileObject sourceFile = filer.createSourceFile("com.hr.util" + className);
                writer = sourceFile.openWriter();
                StringBuffer stringBuffer = new StringBuffer();
                stringBuffer.append("package com.hr.util;\n");
                stringBuffer.append("package com.hr.router;\n" +
                        "\n" +
                        "public class " +className + " implements IRouter {\n" +
                        "    \n" +
                        "    \n" +
                        "    @Override\n" +
                        "    public void putActivity() {\n");
                Iterator<String> iterator = map.keySet().iterator();
                while (iterator.hasNext()) {
                    String key = iterator.next();
                    String value = map.get(key);
                    stringBuffer.append("Arouter.getInstance().addActivity(" + key +"," + value+  ");\n");
                }
                stringBuffer.append("\n}\n}\n");
                writer.write(stringBuffer.toString());


            } catch (IOException e) {
                e.printStackTrace();
            }finally {
                if(writer != null) {
                    try {
                        writer.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return false;
    }
}
