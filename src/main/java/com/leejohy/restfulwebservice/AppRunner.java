package com.leejohy.restfulwebservice;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.MessageSource;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.stereotype.Component;

@Component
public class AppRunner implements ApplicationRunner {

    @Autowired
    private MessageSource messageSource;

    // private ReloadableResourceBundleMessageSource reloadable;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        // Locale.setDefault(Locale.ROOT);
        //
        // while (true) {
        //     System.out.println(
        //         messageSource.getMessage("greeting.message", new String[] {"leejohy", "lucid"}, Locale.getDefault()));
        //     System.out.println(messageSource.getMessage("greeting.message", new String[] {"leejohy", "lucid"}, Locale.US));
        //     System.out.println(messageSource.getMessage("greeting.message", new String[] {"leejohy", "lucid"}, Locale.FRANCE));
        //     Thread.sleep(2000L);
        // }

        while (true) {
            System.out.println(messageSource.getClass());
            Locale.setDefault(Locale.ROOT);
            System.out.println(
                messageSource.getMessage("greeting.message", new String[] {"leejohy", "lucid"}, Locale.getDefault()));
            System.out.println(messageSource.getMessage("greeting.message", new String[] {"leejohy", "lucid"}, Locale.ENGLISH));
            System.out.println(messageSource.getMessage("greeting.message", new String[] {"leejohy", "lucid"}, Locale.FRENCH));
            System.out.println();
            Thread.sleep(1000l);
        }
    }
}
