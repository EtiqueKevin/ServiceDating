<?php


use apiAuth\application\actions\GetUserByRoleAction;
use apiAuth\application\actions\GetUserIDAction;
use apiAuth\application\actions\GetUserRoleAction;
use apiAuth\application\actions\RefreshAction;
use apiAuth\application\actions\RegisterAction;
use apiAuth\application\actions\RegisterSalarieAction;
use apiAuth\application\actions\SignInAction;
use apiAuth\application\actions\ValidateAction;
use apiAuth\application\providers\auth\AuthProvider;
use apiAuth\application\providers\auth\AuthProviderInterface;
use apiAuth\application\providers\auth\JWTManager;
use apiAuth\core\repositoryInterface\AuthRepositoryInterface;
use apiAuth\core\repositoryInterface\GestionRepositoryInterface;
use apiAuth\core\services\auth\AuthService;
use apiAuth\core\services\auth\AuthServiceInterface;
use apiAuth\core\services\user\UserService;
use apiAuth\core\services\user\UserServiceInterface;
use apiAuth\infrastructure\adaptater\AdaptaterGestionRepository;
use apiAuth\infrastructure\repositories\PDOAuthRepository;
use Psr\Container\ContainerInterface;

return [

    AuthRepositoryInterface::class => function (ContainerInterface $container) {
        return new  PDOAuthRepository( $container->get('auth.pdo'));
    },

    GestionRepositoryInterface::class => function (ContainerInterface $container) {
        return new AdaptaterGestionRepository($container->get('client_gestion'));
    },

    AuthServiceInterface::class => function(ContainerInterface $c){
        return new AuthService($c->get(AuthRepositoryInterface::class));
    },

    UserServiceInterface::class => function(ContainerInterface $c){
    return new UserService($c->get(AuthRepositoryInterface::class),$c->get(GestionRepositoryInterface::class));
    },

    AuthProviderInterface::class => function(ContainerInterface $c){
        return new AuthProvider($c->get(AuthServiceInterface::class),$c->get(JWTManager::class));
    },

    SignInAction::class => function(ContainerInterface $c){
        return new SignInAction($c->get(AuthProviderInterface::class));
    },

    RegisterAction::class => function(ContainerInterface $c){
        return new RegisterAction($c->get(UserServiceInterface::class), $c->get(AuthProviderInterface::class));
    },

    RefreshAction::class => function(ContainerInterface $c){
        return new RefreshAction($c->get(AuthProviderInterface::class));
    },

    ValidateAction::class => function(ContainerInterface $c){
        return new ValidateAction($c->get(AuthProviderInterface::class));
    },

    GetUserIDAction::class => function (ContainerInterface $c) {
        return new GetUserIDAction($c->get(AuthProviderInterface::class));
    },

    RegisterSalarieAction::class => function (ContainerInterface $c) {
        return new RegisterSalarieAction($c->get(UserServiceInterface::class));
    },

    GetUserRoleAction::class => function (ContainerInterface $c) {
    return new GetUserRoleAction($c->get(AuthProviderInterface::class));
    },

    GetUserByRoleAction::class => function (ContainerInterface $c) {
        return new GetUserByRoleAction($c->get(UserServiceInterface::class));
    },

    JWTManager::class => function(ContainerInterface $c){
        return new JWTManager($c->get('SECRET_KEY'));
    },

];