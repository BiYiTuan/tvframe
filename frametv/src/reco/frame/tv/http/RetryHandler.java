/**
 * Copyright (c) 2012-2013, Michael Yang ??��??�? (www.yangfuhai.com).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package reco.frame.tv.http;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.HashSet;

import javax.net.ssl.SSLHandshakeException;

import org.apache.http.NoHttpResponseException;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.protocol.ExecutionContext;
import org.apache.http.protocol.HttpContext;

import android.os.SystemClock;

public class RetryHandler implements HttpRequestRetryHandler {
    private static final int RETRY_SLEEP_TIME_MILLIS = 1000;
    
    //�����쳣������
    private static HashSet<Class<?>> exceptionWhitelist = new HashSet<Class<?>>();
    
    //�û��쳣�����������磬�û��ж��̣߳�
    private static HashSet<Class<?>> exceptionBlacklist = new HashSet<Class<?>>();

    static {
        exceptionWhitelist.add(NoHttpResponseException.class);
        exceptionWhitelist.add(UnknownHostException.class);
        exceptionWhitelist.add(SocketException.class);

        exceptionBlacklist.add(InterruptedIOException.class);
        exceptionBlacklist.add(SSLHandshakeException.class);
    }

    private final int maxRetries;

    public RetryHandler(int maxRetries) {
        this.maxRetries = maxRetries;
    }

    @Override
    public boolean retryRequest(IOException exception, int executionCount, HttpContext context) {
        boolean retry = true;

        Boolean b = (Boolean) context.getAttribute(ExecutionContext.HTTP_REQ_SENT);
        boolean sent = (b != null && b.booleanValue());

        if(executionCount > maxRetries) {
            // �?�?次�?��??�???��?��??�????�?�?�?�?�?5�?
            retry = false;
        } else if (exceptionBlacklist.contains(exception.getClass())) {
            // 线�??�???��?�中???�????�?继续�?�?
            retry = false;
        } else if (exceptionWhitelist.contains(exception.getClass())) {
            retry = true;
        } else if (!sent) {
            retry = true;
        }

        if(retry) {
            HttpUriRequest currentReq = (HttpUriRequest) context.getAttribute( ExecutionContext.HTTP_REQUEST );
            retry = currentReq!=null && !"POST".equals(currentReq.getMethod());
        }

        if(retry) {
        	//�????1�??????????继续�?�?
            SystemClock.sleep(RETRY_SLEEP_TIME_MILLIS);
        } else {
            exception.printStackTrace();
        }

        return retry;
    }
    
}