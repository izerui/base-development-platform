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
package xxx.yyy.framework.common.enumeration;

/**
 * Created by serv on 14-3-25.
 */
public enum CalendarType implements ValueEnum<Integer> {
    /**
     * 公历
     */
    GREGORIAN_CALENDAR("sys.base.calendartype.gregorian", 0),

    /**
     * 农历
     */
    LUNAR_CALENDAR("sys.base.calendartype.lunar", 1);

    private String name;

    private int value;


    private CalendarType(String name, int value) {
        this.name = name;
        this.value = value;
    }

    @Override
    public Integer getValue() {

        return value;
    }

    @Override
    public String getName() {

        return name;
    }
}
