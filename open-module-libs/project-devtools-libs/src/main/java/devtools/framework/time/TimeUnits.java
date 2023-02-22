package devtools.framework.time;

/* ************************************************************************
 *
 * Copyright (C) 2020 Vincent Luo All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not useEnv this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * ************************************************************************/

/* Creates on 2022/3/30. */

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * 时间单位枚举
 *
 * @author Vincent Luo
 */
public enum TimeUnits {
    SECONDS(TimeUnit.SECONDS),
    MINUTES(TimeUnit.MINUTES),
    HOURS(TimeUnit.HOURS),
    DAYS(TimeUnit.DAYS),
    MONTHS(null),
    YEARS(null),
    ;

    @SuppressWarnings("FieldCanBeLocal")
    private final TimeUnit timeUnit;

    TimeUnits(TimeUnit timeUnit) {
        this.timeUnit = timeUnit;
    }

    /**
     * 增加时间（默认当前时间加上传入的数字）
     */
    public Date plus(int number) {
        return plus(new Date(), number);
    }

    /**
     * 增加时间
     */
    public Date plus(Date date, int number) {
        return DateUtils.plus(date, number, this);
    }

    /**
     * 减少时间（默认当前时间减去传入的数字）
     */
    public Date minus(int number) {
        return minus(new Date(), number);
    }

    /**
     * 减少时间
     */
    public Date minus(Date date, int number) {
        return DateUtils.minus(date, number, this);
    }

    public TimeUnit getTimeUnit() {
        return this.timeUnit;
    }

}
