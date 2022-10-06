# Challenge

## Primera parte
Dado que el challenge no requeria roles de ningun tipo, se concentraba por los permisos. Se implemento un login, todas las cuentas tienen la contraseña `password`.
## Segunda parte
Dado que el challenge no exigia utilizar algo especifico opte por utilizar un permissionEvaluator, en lugar de un sofisticado sistema de roles y permisos, ACLs.
## Tercera parte
Se utiliza JWT con HMAC512, se podria utilziar RSA pero para simplicidad, se utilizo un secretKey. No se utilizo nada custom, solo jwt de Spring Security.

## Cuarta parte
Se utiliza validacion basica. Se necesita estar logueado para todo lo que es crear. Se incoporta un archivo para utilizar en postman.

## Base de datos
Se requieren las variables de entorno de `DB_URL` `USER` `PASSWORD`
