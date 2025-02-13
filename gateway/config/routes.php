<?php
declare(strict_types=1);

use gateway\application\actions\GeneriqueOptimisationAction;
use gateway\application\actions\GeneriqueAuthAction;
use gateway\application\actions\GeneriqueGestionAction;
use gateway\application\middleware\AuthMiddleware;
use gateway\application\middleware\Cors;
use Psr\Http\Message\ServerRequestInterface as Request;
use Psr\Http\Message\ResponseInterface as Response;
use Slim\App;

return function(App $app): App {

    /*$app->add(Cors::class);

    $app->options('/{routes:.+}', function(Request $rq, Response $rs, array $args): Response {
        return $rs;
    });*/

    /*************************
    * Routes de l'API optimisation
    *************************/



    /*************************
    * Routes de l'API
    *************************/

    $app->post('/besoins/', GeneriqueGestionAction::class);
    $app->get('/besoins/', GeneriqueGestionAction::class);


    /*************************
    * Routes de l'API Auth
    *************************/


    return $app;
};
