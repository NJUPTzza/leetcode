class SingleTon {
    // volatile 关键字修饰变量 防止指令重排序
    public static volatile SingleTon instance = null;

    public SingleTon(){}

    public static SingleTon getInstance() {
        if (instance == null) {
            synchronized(SingleTon.class) {
                if (instance == null) {
                    instance = new SingleTon();
                }
            }
        }
        return instance;
    }
}