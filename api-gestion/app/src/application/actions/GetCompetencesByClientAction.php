<?php

namespace gestion\application\actions;

use gestion\core\services\GestionServiceInterface;
use Psr\Http\Message\ResponseInterface;
use Psr\Http\Message\ServerRequestInterface;

class GetCompetencesByClientAction extends AbstractAction
{
    private GestionServiceInterface $gestion_service;

    public function __construct(GestionServiceInterface $gestion_service)
    {
        $this->gestion_service = $gestion_service;
    }

    public function __invoke(ServerRequestInterface $rq, ResponseInterface $rs, array $args): ResponseInterface
    {
        $id = $args['id'];
        $competences = $this->gestion_service->getCompetencesByClient($id);

        $res  = [
          "type" => "collection",
          "competences" => $competences
        ];

        $rs->getBody()->write(json_encode($res));
        return $rs->withHeader('Content-Type', 'application/json')->withStatus(200);
    }
}