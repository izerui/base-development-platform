/**
 * Copyright (C) 2014 serv (liuyuhua69@gmail.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package xxx.yyy.sys.base.jpa.impl;


import xxx.yyy.sys.base.jpa.PlatformJpaRepository;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Created by serv on 2014/6/22.
 */
public class ProxyHandler implements InvocationHandler{


    private Object target;

    public ProxyHandler(Object target) {
        this.target = target;
    }

    /**
     * 获取目标对象的代理对象
     * @return 代理对象
     */
    public Object getProxy(){
        return Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(),
                target.getClass().getInterfaces(), this);
    }


    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        Object result = method.invoke(target, args);

//        Class[] argsClass = null;
//
//        if(args!=null){
//            argsClass = new Class[args.length];
//            for(int i=0;i<args.length;i++){
//
//                ParameterizedType
//
//                argsClass[i] = args[i].getClass().getp;
//            }
//        }

        Method platformImplMethod = target.getClass().getMethod(method.getName(),method.getParameterTypes());

        if(platformImplMethod.isAnnotationPresent(ClearParamAfterMethod.class)){
            ((PlatformJpaRepository)target).clearParamAppended();
        }

        return result;
    }
}
