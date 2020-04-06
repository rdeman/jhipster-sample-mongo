import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './data.reducer';
import { IData } from 'app/shared/model/data.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IDataDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const DataDetail = (props: IDataDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { dataEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          <Translate contentKey="jhipsterSampleMongoApp.data.detail.title">Data</Translate> [<b>{dataEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="revision">
              <Translate contentKey="jhipsterSampleMongoApp.data.revision">Revision</Translate>
            </span>
          </dt>
          <dd>{dataEntity.revision}</dd>
          <dt>
            <Translate contentKey="jhipsterSampleMongoApp.data.classification">Classification</Translate>
          </dt>
          <dd>{dataEntity.classificationId ? dataEntity.classificationId : ''}</dd>
          <dt>
            <Translate contentKey="jhipsterSampleMongoApp.data.master">Master</Translate>
          </dt>
          <dd>{dataEntity.masterId ? dataEntity.masterId : ''}</dd>
        </dl>
        <Button tag={Link} to="/data" replace color="info">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/data/${dataEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ data }: IRootState) => ({
  dataEntity: data.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(DataDetail);
