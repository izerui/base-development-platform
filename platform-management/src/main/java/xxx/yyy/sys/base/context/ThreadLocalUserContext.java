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
package xxx.yyy.sys.base.context;

import xxx.yyy.sys.rbac.model.User;

import java.util.Stack;

/**
 * for computer no session
 * Created by serv
 */
public class ThreadLocalUserContext {

    protected static ThreadLocal<Stack<User>> localUserThread = new ThreadLocal<Stack<User>>();

    public static User getUser(){
        Stack<User> stack = localUserThread.get();

        if(null==stack){
            return null;
        }

        return stack.pop();
    }

    public static void setUser(User user){
        Stack<User> stack = localUserThread.get();
        if(stack==null){
            stack = new Stack<User>();
        }
        stack.push(user);
        localUserThread.set(stack);

    }
}
