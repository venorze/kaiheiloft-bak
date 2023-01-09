package com.hantiansoft.qiniu.test;

/* ************************************************************************
 *
 * Copyright (C) 2020 Vincent Luo All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
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

/* Creates on 2023/1/9. */

import com.hantiansoft.adapter.SourcePolicy;
import com.hantiansoft.adapter.StoreAdapter;
import com.hantiansoft.qiniu.QiniuSourcePolicy;
import org.junit.Test;

import java.util.Properties;

/**
 * No Descript.
 *
 * @author Vincent Luo
 */
public class T_UPLOAD_FILE {

    @Test
    public void uploadLocalFile() {
        Properties props = new Properties();
        props.put("access", "tchS3evhxj_qcf_x9JIJlZDD7Xv83fNTMuARj8Xp");
        props.put("secret", "hB22f61gS6art-d2XDxvCl-ka3gcovxfuaKncbBp");
        props.put("bucket", "store-distance-avatar");
        SourcePolicy sourcePolicy = StoreAdapter.createSourcePolicy(QiniuSourcePolicy.class, props);

        System.out.println("save location: " + sourcePolicy.putFile("C:\\Users\\open-\\Desktop\\doge.jpg", "avatar/doge.jpg"));
    }

}
