# rw-spring-boot-starter

### List of AutoConfigurations

### To Do

1. MailerService depends on Jakarta Mail API.
   Ideally, the notification system should be more generic.

1. There are a few components which aren't registered.

### Known Issues

1. `springdoc` aka `swagger-ui` does not work well with CSRF security.
   You have to execute the POST (or other) request twice for some reason.
