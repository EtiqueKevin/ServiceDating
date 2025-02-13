<?php

namespace gestion\application\actions;

use gestion\core\dto\InputPutBesoinDTO;
use gestion\core\services\GestionServiceInterface;
use Psr\Http\Message\ResponseInterface;
use Psr\Http\Message\ServerRequestInterface;

class PutBesoinByIdAction extends AbstractAction
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
        $besoin_id = $args['id'];
        $inputPutBesoinDTO = new InputPutBesoinDTO($besoin_id, $id, $body['competence_id'], $body['description']);
        $BesoinDTO = $this->gestionService->modifierBesoin($inputPutBesoinDTO);
        $res = [
            'type' => 'ressources',
            'besoin' => $BesoinDTO
        ];

        $rs->getBody()->write(json_encode($res));
        return $rs->withHeader('Content-Type', 'application/json')->withStatus(200);
    }
}