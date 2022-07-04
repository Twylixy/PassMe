# PassMe - authentication plugin
The simplest and lightweight authentication plugin for **Bukkit/Spigot/Paper** servers.

## Features
* Protection for player's accounts
* Strong password encryption, provided by [Argon2](https://github.com/P-H-C/phc-winner-argon2) (used [Argon2-jvm](https://github.com/phxql/argon2-jvm))
* Flexible configuration
* Sessions for logins

### Versions
**Minecraft**: `1.19` only \
**Server core**: `Bukkit`, `Spigot` and `Paper`

### Permissions
There is only one permission - `passme.reload` it provides access to `/passme reload` command

### Commands
* `/register` (alias: `/reg`) - register an account
  * if executed from `console` will provide player registration (`/reg <name> <password>`)
* `/changepassword` (alias: `/cp`) - changes password
  * if executed from `console` will provide changing for specified player (`/cp <name> <new_password>`)
* `/login` (alias `/l`) - login into account
* `/logout` - logout from account
* `/passme` - information about plugin
  * `/passme reload` - reloads plugin's config

### When I can use it?
**Answer**: only on small servers \
PassMe was created for simple and small servers, like for server for you and your friends. It does not suitable for large servers, if you are looking for authentication plugin which will be suitable for large servers, please, use [AuthMeReloaded](https://github.com/AuthMe/AuthMeReloaded) or yours own.

