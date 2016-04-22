package pl.bigbook;

import rx.Subscription;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.subjects.BehaviorSubject;
import rx.subjects.PublishSubject;
import rx.subjects.ReplaySubject;
import rx.subscriptions.Subscriptions;

/**
 * Based on https://github.com/Froussios/Intro-To-RxJava
 */
public class App {

    public static void main(String[] args) {
        App app = new App();
        app.onCompleted();
    }

    //   https://github.com/Froussios/Intro-To-RxJava/blob/master/Part%201%20-%20Getting%20Started/2.%20Key%20types.md#publishsubject
    private void publishSubject() {
        PublishSubject<String> subject = PublishSubject.create();
        subject.onNext("a");
        subject.onNext("bb");
        subject.subscribe(System.out::println);//Functions without a return type are also called actions.
        subject.onNext("ccc");
        subject.onNext("dddd");
        subject.subscribe(s -> {
            System.out.println("1|" + s.length());
        });
        subject.subscribe(new Action1<String>() {
            @Override
            public void call(String s) {
                System.out.println("2|" + s.toUpperCase());
            }
        });
        subject.onNext("eeeee");
        subject.onNext("ffffff");
        subject.onNext("ggggggg");
        subject.onNext("hhhhhhhh");
    }

/*
    ReplaySubject has the special feature of caching all the values pushed to it. When a new subscription is made,
    the event sequence is replayed from the start for the new subscriber. After catching up, every subscriber
    receives new events as they come.
*/
    private void replaySubject() {
        ReplaySubject<String> subject = ReplaySubject.create(); //createWithSize(2);
        subject.onNext("a");
        subject.onNext("bb");

        subject.subscribe(System.out::println);

        subject.onNext("ccc");
        subject.onNext("dddd");
        subject.onNext("eeeee");
        subject.onNext("hhhhhh");

        subject.subscribe(s -> {
            System.out.println("1|" + s.length());
        });

        subject.onNext("fffffff");
        subject.onNext("gggggggg");

        subject.subscribe(new Action1<String>() {
            @Override
            public void call(String s) {
                System.out.println("2|" + s.toUpperCase());
            }
        });
    }

/*
    BehaviorSubject only remembers the last value. It is similar to a ReplaySubject with a buffer of size 1.
    An initial value can be provided on creation, therefore guaranteeing that a value always will be available
    immediately on subscription.
*/
    private void behaviorSubject() {
        BehaviorSubject<String> subject = BehaviorSubject.create(); //createWithSize(2);
        subject.onNext("a");
        subject.onNext("bb");

        subject.subscribe(System.out::println);

        subject.onNext("ccc");
        subject.onNext("dddd");
        subject.onNext("eeeee");
        subject.onNext("hhhhhh");

        subject.subscribe(s -> {
            System.out.println("1|" + s.length());
        });

        subject.onNext("fffffff");
        subject.onNext("gggggggg");

        subject.subscribe(new Action1<String>() {
            @Override
            public void call(String s) {
                System.out.println("2|" + s.toUpperCase());
            }
        });
    }

    /*
        BehaviorSubject only remembers the last value. It is similar to a ReplaySubject with a buffer of size 1.
        An initial value can be provided on creation, therefore guaranteeing that a value always will be available
        immediately on subscription.
    */
    private void subjectOnCompleted() {
        PublishSubject<String> subject = PublishSubject.create();
        subject.onNext("a");
        subject.onNext("bb");

        subject.subscribe(System.out::println);

        subject.onNext("ccc");
        subject.onNext("dddd");
        subject.onCompleted();
        subject.onNext("eeeee");
        subject.onNext("hhhhhh");

        subject.subscribe(s -> {
            System.out.println("1|" + s.length());
        });

        subject.onNext("fffffff");
        subject.onNext("gggggggg");

        subject.subscribe(new Action1<String>() {
            @Override
            public void call(String s) {
                System.out.println("2|" + s.toUpperCase());
            }
        });
    }

    private void unsubscribeAndOnError() {
        PublishSubject<String> subject = PublishSubject.create();
        Subscription subscription = subject.subscribe(v -> System.out.println(v), e-> System.out.println(e));

        subject.onNext("a");
        subject.onNext("bb");
        subject.onNext("ccc");

        subscription.unsubscribe();

        subject.onNext("dddd");
        subject.onError(new Exception("Some error occur!"));
        subject.onNext("eeeee");
        subject.onNext("eeeee");
        subject.onNext("hhhhhh");

        subject.subscribe(s -> System.out.println("1|" + s.length()), e->{
            System.out.println(e);
        });

        subject.onNext("fffffff");
        subject.onNext("gggggggg");
    }

    //https://github.com/Froussios/Intro-To-RxJava/blob/master/Part%201%20-%20Getting%20Started/3.%20Lifetime%20management.md#freeing-resources
    private void onCompleted() {
        ReplaySubject<String> subject = ReplaySubject.create();
        subject.subscribe(v -> System.out.println(v), e-> System.out.println(e));
        Subscription subscription = Subscriptions.create(() -> System.out.println("Clean"));


        Subscription  subscription1 = Subscriptions.empty();


        subject.onNext("a");
        subject.onNext("bb");
        subject.onNext("ccc");

        subject.onNext("dddd");
        subject.onCompleted();
        subject.onNext("eeeee");
        subject.onNext("eeeee");
        subject.onNext("hhhhhh");

        subject.subscribe(s -> System.out.println("1|" + s.length()));

        subject.onNext("fffffff");
        subject.onNext("gggggggg");
    }

}
