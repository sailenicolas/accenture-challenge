# Challenge

## Primera parte
Dado que el challenge no requería roles de ninguno tipo, se concentraba por los permisos. Se implementó un login, todas las cuentas tienen la contraseña `password`.
## Segunda parte
Dado que el challenge no exigió utilizar algo específico opte por utilizar un permissionEvaluator, en lugar de un sofisticado sistema de roles y permisos, ACL.
## Tercera parte
Se utiliza JWT con HMAC512, se podría utilizar RSA, pero para simplicidad, se utilizó un secretKey. No se usó nada custom, solo jwt de Spring Security.

## Cuarta parte
Se utiliza validación básica. Se necesita estar conectado para todo lo que es crear, además incorpora un archivo para usar en postman.

## Base de datos
Se requieren las variables de entorno de `DB_URL` `USER` `PASSWORD`, se utilizó MySQL con hibernate.

