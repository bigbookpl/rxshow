package pl.bigbook;

import rx.Observable;
import rx.functions.Action1;

import java.util.ArrayList;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
        App app = new App();
        app.rxTest1("marek","zenek","staszek");
    }

    public void rxTest1(String... names){
        Observable.from(names).subscribe(new Action1<String>() {

            @Override
            public void call(String s) {
                System.out.println("Hello " + s + "!");
            }

        });
    }

    public void rxTest2(){
        String[] input = new String[10];
        Observable<String> o = Observable.from(input);

    }
}
