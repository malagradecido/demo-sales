package com.demo.sales.config;

import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListenerConfigurer;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistrar;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.handler.annotation.support.DefaultMessageHandlerMethodFactory;
//import org.springframework.retry.backoff.ExponentialBackOffPolicy;
//import org.springframework.retry.support.RetryTemplate;

@Configuration
@EnableRabbit
public class RabbitConfig implements RabbitListenerConfigurer {
	
	public final static String myQueueName = "my-queue";
	public final static String deadLetterMyQueueName = "dead-letter-my-queue";
	public final static String myWorkQueueName = "my-work-queue";
	
	@Bean
    public ConnectionFactory connectionFactory() {
        return new CachingConnectionFactory("localhost");
    }

    @Bean
    public AmqpAdmin amqpAdmin() {
        return new RabbitAdmin(connectionFactory());
    }

    @Bean
    public RabbitTemplate rabbitTemplate() {
        
        RabbitTemplate template = new RabbitTemplate(connectionFactory());
        template.setMessageConverter(jackson2JsonMessageConverter());
//        RetryTemplate retryTemplate = new RetryTemplate();
//        ExponentialBackOffPolicy backOffPolicy = new ExponentialBackOffPolicy();
//        backOffPolicy.setInitialInterval(500);
//        backOffPolicy.setMultiplier(10.0);
//        backOffPolicy.setMaxInterval(10000);
//        retryTemplate.setBackOffPolicy(backOffPolicy);
//        template.setRetryTemplate(retryTemplate);
        return template;
    }
	
	/*
	 * Cola simple. productor envia, consumidor recibe
	 */
    
    @Bean
    Queue myQueue() {
//        return new Queue(myQueueName, false);
    	return QueueBuilder.durable(myQueueName)
        .withArgument("x-dead-letter-exchange", "")
        .withArgument("x-dead-letter-routing-key", deadLetterMyQueueName)
        .build();
    }
    
    @Bean
    Queue deadLetterMyQueue() {
        return QueueBuilder.durable( deadLetterMyQueueName ).build();
    }
    
	@Bean
	DirectExchange myQueueExchange() {
		return new DirectExchange("my-queue-exchange");
	}
	
	@Bean
    Binding myQueueBinding() {
        return BindingBuilder.bind( myQueue() ).to( myQueueExchange() ).with( myQueueName );
    }
	
	/*
	 * Cola de trabajo compartido. productores envian varios mensajes y consumidores comparten
	 * el trabajo recibido equitativamente
	 */
	
	@Bean
    Queue myWorkQueue() {
		//Cualquier mensaje que se encuentre en la cola no sera eliminado
		//asi se reinicie el servidor rabbitmq
		return new Queue(myWorkQueueName, true);
    }
	
	@Bean
    Binding myWorkQueueBinding() {
        return BindingBuilder.bind(
        		myWorkQueue() ).to( myWorkQueueExchange() ).with( myWorkQueueName );
    }
	
	@Bean
	DirectExchange myWorkQueueExchange() {
		return new DirectExchange("my-work-queue-exchange");
	}
	
	@Bean
    public Jackson2JsonMessageConverter jackson2JsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
	}
	
	@Bean
	public MappingJackson2MessageConverter consumerJackson2MessageConverter() {
	   return new MappingJackson2MessageConverter();
	}
	 
	@Bean
	public DefaultMessageHandlerMethodFactory messageHandlerMethodFactory() {
	   DefaultMessageHandlerMethodFactory factory = new DefaultMessageHandlerMethodFactory();
	   factory.setMessageConverter(consumerJackson2MessageConverter());
	   return factory;
	}
	 
	@Override
	public void configureRabbitListeners(final RabbitListenerEndpointRegistrar registrar) {
	   registrar.setMessageHandlerMethodFactory(messageHandlerMethodFactory());
	}
	
//	@Bean
//	public RabbitListenerContainerFactory<SimpleMessageListenerContainer> prefetchTwoRabbitListenerContainerFactory() {
//	    SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
//	    factory.setConnectionFactory(connectionFactory());
//	    factory.setPrefetchCount(2);
//	    return factory;
//	}

    /*@Bean
    TopicExchange exchange() {
        return new TopicExchange("spring-boot-exchange");
    }*/
}