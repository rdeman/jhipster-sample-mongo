import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IData } from 'app/shared/model/data.model';
import { getEntities as getData } from 'app/entities/data/data.reducer';
import { getEntity, updateEntity, createEntity, reset } from './property-set.reducer';
import { IPropertySet } from 'app/shared/model/property-set.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IPropertySetUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const PropertySetUpdate = (props: IPropertySetUpdateProps) => {
  const [dataId, setDataId] = useState('0');
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { propertySetEntity, data, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/property-set');
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getData();
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const entity = {
        ...propertySetEntity,
        ...values
      };

      if (isNew) {
        props.createEntity(entity);
      } else {
        props.updateEntity(entity);
      }
    }
  };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="jhipsterSampleMongoApp.propertySet.home.createOrEditLabel">
            <Translate contentKey="jhipsterSampleMongoApp.propertySet.home.createOrEditLabel">Create or edit a PropertySet</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : propertySetEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="property-set-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="property-set-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="nameLabel" for="property-set-name">
                  <Translate contentKey="jhipsterSampleMongoApp.propertySet.name">Name</Translate>
                </Label>
                <AvField id="property-set-name" type="text" name="name" />
              </AvGroup>
              <AvGroup>
                <Label for="property-set-data">
                  <Translate contentKey="jhipsterSampleMongoApp.propertySet.data">Data</Translate>
                </Label>
                <AvInput id="property-set-data" type="select" className="form-control" name="dataId">
                  <option value="" key="0" />
                  {data
                    ? data.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.id}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/property-set" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">
                  <Translate contentKey="entity.action.back">Back</Translate>
                </span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp;
                <Translate contentKey="entity.action.save">Save</Translate>
              </Button>
            </AvForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

const mapStateToProps = (storeState: IRootState) => ({
  data: storeState.data.entities,
  propertySetEntity: storeState.propertySet.entity,
  loading: storeState.propertySet.loading,
  updating: storeState.propertySet.updating,
  updateSuccess: storeState.propertySet.updateSuccess
});

const mapDispatchToProps = {
  getData,
  getEntity,
  updateEntity,
  createEntity,
  reset
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(PropertySetUpdate);
