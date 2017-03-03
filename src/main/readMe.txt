1. 解决误点
ThreadPoolExecutor executor = new ThreadPoolExecutor(……);
executor.execute(cTread)    ——》该方法是提交了一个任务。
真正运行的线程是 线程池中的线程， 而executor.execute(),executor.submit()的行为是提交任务。
并非是将我们建立的线程放入线程池，其实 cThread对线程池来说是一个任务。
故，
 CThead cThread = new CThread();
for(int i = 0;i<50;i++){
    executor.executor(cThread);
}
线程池一共运行了50个任务。至于线程池Executor中有多少个线程是不知道的，是动态的，根据new ThreadPoolExecutor()参数来的。
