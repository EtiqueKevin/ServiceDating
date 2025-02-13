<?php

namespace gestion\application\actions;

use gestion\core\dto\InputBesoinDTO;
use gestion\core\services\GestionServiceInterface;
use Psr\Http\Message\ResponseInterface;
use Psr\Http\Message\ServerRequestInterface;

class PostBesoinAction extends AbstractAction
{
    private GestionServiceInterface $gestionService;

    public function __construct(GestionServiceInterface $gestionService)
    {
        $this->gestionService = $gestionService;
    }

    public function __invoke(ServerRequestInterface $rq, ResponseInterface $rs, array $args): ResponseInterface
    {
        $id = $rq->getAttribute('idUser');
        $body = $rq->getParsedBody();

        $inputBesoinDTO = new InputBesoinDTO($id, $body['competence_id'], $body['description']);

        $besoinDTO = $this->gestionService->creerBesoin($inputBesoinDTO);

        $res = [
            'type' => 'ressources',
            'besoin' => $besoinDTO
        ];

        $rs->getBody()->write(json_encode($res));
        return $rs->withHeader('Content-Type', 'application/json')->withStatus(201);
    }
}