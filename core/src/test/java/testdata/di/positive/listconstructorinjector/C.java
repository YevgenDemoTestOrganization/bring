package testdata.di.positive.listconstructorinjector;

import com.bobocode.bring.core.annotation.Component;

@Component
public class C implements IA {

    @Override
    public void talk() {
        System.out.println("Talk from C");
    }
}
