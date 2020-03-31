package com.example.myapplication33;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import mockit.Expectations;
import mockit.Injectable;
import mockit.Mock;
import mockit.MockUp;
import mockit.Mocked;
import mockit.Tested;
import mockit.integration.junit4.JMockit;

@RunWith(JMockit.class)    //@Tested与@Injectable搭配使用
public class TestedAndMockup {
    //@Tested修饰的类，表示是我们要测试对象,在这里表示，我想测试订单服务类。JMockit也会帮我们实例化这个测试对象
    @Tested
    OrderService orderService;
    //测试用户ID
    @Injectable
    UserCheckService userCheckService;
    @Injectable
    MailService mailService;
    long testUserId = 123456l;
    //测试商品id
    long testItemId = 456789l;

    // 测试注入方式
    @Test
    public void testSubmitOrder() {
        // 手工通过MockUp创建这个接口的实例
        UserCheckService userCheckService = new MockUp<UserCheckService>(UserCheckService.class) {
            // 对方法Mock
            @Mock
            public boolean check(long userId) {
                return true;
            }
        }.getMockInstance();
        MailService mailService = new MockUp<MailService>(MailService.class) {
            // 对方法Mock
            @Mock
            public boolean sendMail(long userId, String content) {
                return true;
            }
        }.getMockInstance();
        orderService.setMailService(mailService);
        orderService.setUserCheckService(userCheckService);

        Assert.assertTrue(mailService.sendMail(testUserId, "11"));
        Assert.assertTrue(userCheckService.check(testUserId));
        Assert.assertTrue(orderService.submitOrder(testUserId, testItemId));
    }
}