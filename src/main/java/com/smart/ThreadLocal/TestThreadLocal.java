package com.smart.ThreadLocal;

/**
 * ThreadLocal创建副本的过程：
 * 1、首先，在每个线程Thread内部有一个ThreadLocal.ThreadLocalMap类型的成员变量threadLocals，这个threadLocals就是用来
 * 存储实际的变量副本的，键值为当前ThreadLocal变量，value为变量副本。
 * 2、初始时，在Thread里面，threadLocals为空，当通过ThreadLocal变量调用get()方法或者set()方法，就会对Thread类中的threadLocals
 * 进行初始化，并且以当前ThreadLocal变量为键值,以ThreadLocal要保存的副本变量为value，存到threadLocals中。
 * 3、最后，在当前线程里面，如果要使用副本变量，就可以通过get()方法在threadLocals里面查找。
 */

/**
 * 总结：1、实际的通过ThreadLocal创建的副本是存储在每个线程自己的threadLocals中的。
 *      2、为何threadLocals的类型ThreadLocalMap的键值为ThreadLocal对象，因为每个线程中可能有多个ThreadLocal变量，就像下面
 *         代码中的longThreadLocal和stringThreadLocal；
 *      3、在进行get之前，必须先set，否则会报空指针异常；
 *         如果想在get之前不需要调用set就能正常访问的话，必须重写initialValue方法。
 *         因为在源码中，如果没有先set的话，即在map中查找不到相应的存储，则会通过调用setInitialValue方法返回，
 *         而在setInitialValue方法中，有一个语句是 T value = initialValue();，initialValue方法返回的是null。
 *         /**
 *         public T get() {
                Thread t = Thread.currentThread();
                ThreadLocalMap map = getMap(t);
                 if (map != null) {
                 ThreadLocalMap.Entry e = map.getEntry(this);
                 if (e != null)
                 return (T)e.value;
                 }
                 return setInitialValue();
            }
            *
 *          private T setInitialValue() {
                 T value = initialValue();
                 Thread t = Thread.currentThread();
                 ThreadLocalMap map = getMap(t);
                 if (map != null)
                 map.set(this, value);
                 else
                 createMap(t, value);
                 return value;
            }
 *          **
 */
public class TestThreadLocal {

    ThreadLocal<Long> longThreadLocal = new ThreadLocal<Long>() {
        protected Long initialValue() {
            return Thread.currentThread().getId();
        }
    };

    ThreadLocal<String> stringThreadLocal = new ThreadLocal<String>() {
        protected String initialValue() {
            return Thread.currentThread().getName();
        }
    };

    public void set() {
        longThreadLocal.set(Thread.currentThread().getId());
        stringThreadLocal.set(Thread.currentThread().getName());
    }

    public long getLong() {
        return longThreadLocal.get();
    }

    public String getString() {
        return stringThreadLocal.get();
    }

    public static void main(String[] args) throws InterruptedException {
        final TestThreadLocal testThreadLocal = new TestThreadLocal();
//        testThreadLocal.set();
        System.out.println(testThreadLocal.getLong());
        System.out.println(testThreadLocal.getString());

        Thread t1 = new Thread() {
            public void run() {
                testThreadLocal.set();
                System.out.println(testThreadLocal.getLong());
                System.out.println(testThreadLocal.getString());
            }
        };
        t1.start();
        t1.join();

        System.out.println(testThreadLocal.getLong());
        System.out.println(testThreadLocal.getString());
    }

}
