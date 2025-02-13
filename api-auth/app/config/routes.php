<?php
declare(strict_types=1);

use apiAuth\application\actions\GetUserIDAction;
use apiAuth\application\actions\GetUserRoleAction;
use apiAuth\application\actions\HomeAction;
use apiAuth\application\actions\RefreshAction;
use apiAuth\application\actions\RegisterAction;
use apiAuth\application\actions\RegisterSalarieAction;
use apiAuth\application\actions\SignInAction;
use apiAuth\application\actions\ValidateAction;
use Slim\App;

return function( App $app): App {

    //$app->add(Cors::class);
/*
    $app->options('/{routes:.+}',
        function( Request $rq,
                  Response $rs, array $args) : Response {
            return $rs;
        });
*/

    $app->get('/',HomeAction::class);

    $app->post('/signin[/]',SignInAction::class)
        ->setName('tokenSignin');

    $app->post('/register[/]',RegisterAction::class)
        ->setName('tokenRegister');

    $app->post('/token/refresh[/]',RefreshAction::class)
        ->setName('tokenRefresh');

    $app->post('/token/validate[/]',ValidateAction::class)
        ->setName('tokenValidate');

    $app->post('/token/user/id[/]',GetUserIDAction::class);

    $app->post('/register/salarie[/]',RegisterSalarieAction::class);

    $app->get('/token/user/role[/]',GetUserRoleAction::class);
    return $app;
};