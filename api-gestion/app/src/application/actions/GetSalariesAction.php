<?php

namespace gestion\application\actions;

use gestion\core\services\GestionServiceInterface;
use Psr\Http\Message\ResponseInterface;
use Psr\Http\Message\ServerRequestInterface;

class GetSalariesAction extends AbstractAction
{
    private GestionServiceInterface $gestionService;

    public function __construct(GestionServiceInterface $gestionService)
    {
        $this->gestionService = $gestionService;
    }

    public function __invoke(ServerRequestInterface $rq, ResponseInterface $rs, array $args): ResponseInterface
    {
        $salaries = $this->gestionService->getSalaries();
        $res = [
            'type' => 'ressources',
            'salaries' => $salaries
        ];
        $rs->getBody()->write(json_encode($res));
        return $rs->withHeader('Content-Type', 'application/json');
    }
}