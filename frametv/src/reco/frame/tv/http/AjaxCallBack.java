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
/**
 * 
 * @author michael
 *
 * @param <T> Ŀǰ����֧�� String,File, �Ժ���չ��JSONObject,Bitmap,byte[],XmlDom
 */
public abstract class AjaxCallBack<T> {
	
	private boolean progress = true;
	private int rate = 1000 * 1;//�?�?
	
//	private Class<T> type;
//	
//	public AjaxCallBack(Class<T> clazz) {
//		this.type = clazz;
//	}
	
	
	public boolean isProgress() {
		return progress;
	}
	
	public int getRate() {
		return rate;
	}
	
	/**
	 * ���ý���,����ֻ��������������Ժ�onLoading������Ч��
	 * @param progress �Ƿ����ý�����ʾ
	 * @param rate ���ȸ���Ƶ��
	 */
	public AjaxCallBack<T> progress(boolean progress , int rate) {
		this.progress = progress;
		this.rate = rate;
		return this;
	}
	
	public void onStart(){};
	/**
	 * onLoading������Чprogress
	 * @param count
	 * @param current
	 */
	public void onLoading(long count,long current){};
	public void onSuccess(T t){};
	public void onFailure(Throwable t,int errorNo ,String strMsg){};
}
