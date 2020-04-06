import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './property-set.reducer';
import { IPropertySet } from 'app/shared/model/property-set.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IPropertySetDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const PropertySetDetail = (props: IPropertySetDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { propertySetEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          <Translate contentKey="jhipsterSampleMongoApp.propertySet.detail.title">PropertySet</Translate> [<b>{propertySetEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="name">
              <Translate contentKey="jhipsterSampleMongoApp.propertySet.name">Name</Translate>
            </span>
          </dt>
          <dd>{propertySetEntity.name}</dd>
          <dt>
            <Translate contentKey="jhipsterSampleMongoApp.propertySet.data">Data</Translate>
          </dt>
          <dd>{propertySetEntity.dataId ? propertySetEntity.dataId : ''}</dd>
        </dl>
        <Button tag={Link} to="/property-set" replace color="info">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/property-set/${propertySetEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ propertySet }: IRootState) => ({
  propertySetEntity: propertySet.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(PropertySetDetail);
