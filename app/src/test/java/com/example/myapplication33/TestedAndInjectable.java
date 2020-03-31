package com.example.myapplication33;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import mockit.Expectations;
import mockit.Injectable;
import mockit.Tested;
import mockit.integration.junit4.JMockit;

@RunWith(JMockit.class)
public class TestedAndInjectable {
   //@Tested修饰的类，表示是我们要测试对象,在这里表示，我想测试订单服务类。JMockit也会帮我们实例化这个测试对象
   @Tested
   OrderService orderService;
   //测试用户ID
   long testUserId = 123456l;
   //测试商品id
   long testItemId = 456789l;

 
   // 测试注入方式
   @Test
   public void testSubmitOrder(@Injectable final MailService mailService) {
    new Expectations() {
       {
         // 当向testUserId发邮件时，假设都发成功了
         mailService.sendMail(testUserId, anyString);
         result = true;
        // 当检验testUserId的身份时，假设该用户都是合法的
//         userCheckService.check(testUserId);
//        result = true;
         }
         };
    // JMockit帮我们实例化了mailService了，并通过OrderService的构造函数，注入到orderService对象中。 
    //JMockit帮我们实例化了userCheckService了，并通过OrderService的属性，注入到orderService对象中。 
    Assert.assertTrue(orderService.submitOrder(testUserId, testItemId));
    }
}