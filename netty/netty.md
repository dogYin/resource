### TCP粘包和拆包发生的原因
	a,应用程序write写入的字节大小大于套接口发送缓冲区大小
	b.进行MSS大小的TCP分段
	c.以太网帧的payload大于MTU进行IP分片
### 粘包解决策略
	a.消息定长发送
	b.在包尾增加回车换行符进行分割
	c.将消息分为消息头和消息体，消息头中包含表示消息总长度的字段，通常设计思路为消息头的第一个字段使用int32来表示消息总长度
	d.更复杂的应用层协议
### netty中的编码
#### LineBasedFrameDecoder
LineBasedDecoder工作原理是依次遍历ByteBuffer中的可读字节，判断是否有“\n”或者“\r\n”,如果有，就以此位置为结束位置，从可读索引到结束位置区间的字节就组成了一行。
#### DelimiterBasedFrameDecoder
该编码器通过检测特殊分隔符
#### FixedLengthFrameDecoder
该编码器是通过消息定长
### 编码器技术
#### java序列化的缺点	
	a.序列化无法跨语言
	b.序列化后码流太大
	c.序列化性能太低
#### 主流的编解码框架
##### Protobuf(google)
	特点：
		a.结构化数据储存格式（xml,json）
		b.高效的编码性能
		c.语言无关，平台无关，扩展性好
	注意：
		在netty中使用时，由于其不支持读半包，因此在ProtobufDecoder前面一定要有能处理半包的解码器
		三种选择：使用ProtobufVarint32FrameDecoder，继承LengthFieldFrameDecoder,继承ByteToMessageDecoder
##### Thrift(Facebook)
	特点：
		a.支持多语言通信
		b.支持数据序列化和多种RPC服务
	缺点：
		对于静态数据交换，需要先确定好数据结构，当数据结构发生变化时，必须重新编辑IDL文件，生成代码和编译
##### JBoss Marshalling

### Netty多协议开发和应用
#### HTTP协议栈开发
	弊端：
		a,http协议为半双工协议
		b.http消息冗长而繁琐
		c.针对服务器推动的黑客攻击
#### websocket协议栈开发
	特点：
		a.单一的TCP链接，采用全双工模式通信
		b.对代理，防火墙和路由器透明
		c.无头部信息，Cookie和身份验证
		d.无安全开销
		e.通过“ping/pong”帧保持链路激活
		f.服务器可以主动传送消息给客户端，不再需要客户端轮训
## 源码解读
### ByteBuf
	NIO中ByteBuffer缺点：
		1.ByteBuffer长度固定，一旦分配完成不可动态扩容或者收容
		2.ByteBuffer只有一个标识位置的position，读写的时候需要手工调用flip和rewind方法
		3.ByteBufferAPI有限
	Netty中的ByteBuf

		
		