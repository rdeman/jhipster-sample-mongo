import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { getEntity, updateEntity, createEntity, reset } from './data-master.reducer';
import { IDataMaster } from 'app/shared/model/data-master.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IDataMasterUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const DataMasterUpdate = (props: IDataMasterUpdateProps) => {
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { dataMasterEntity, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/data-master');
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const entity = {
        ...dataMasterEntity,
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
          <h2 id="jhipsterSampleMongoApp.dataMaster.home.createOrEditLabel">
            <Translate contentKey="jhipsterSampleMongoApp.dataMaster.home.createOrEditLabel">Create or edit a DataMaster</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : dataMasterEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="data-master-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="data-master-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="nameLabel" for="data-master-name">
                  <Translate contentKey="jhipsterSampleMongoApp.dataMaster.name">Name</Translate>
                </Label>
                <AvField id="data-master-name" type="text" name="name" />
              </AvGroup>
              <AvGroup>
                <Label id="typeLabel" for="data-master-type">
                  <Translate contentKey="jhipsterSampleMongoApp.dataMaster.type">Type</Translate>
                </Label>
                <AvInput
                  id="data-master-type"
                  type="select"
                  className="form-control"
                  name="type"
                  value={(!isNew && dataMasterEntity.type) || 'MATERIAL'}
                >
                  <option value="MATERIAL">{translate('jhipsterSampleMongoApp.DataType.MATERIAL')}</option>
                  <option value="TEST">{translate('jhipsterSampleMongoApp.DataType.TEST')}</option>
                </AvInput>
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/data-master" replace color="info">
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
  dataMasterEntity: storeState.dataMaster.entity,
  loading: storeState.dataMaster.loading,
  updating: storeState.dataMaster.updating,
  updateSuccess: storeState.dataMaster.updateSuccess
});

const mapDispatchToProps = {
  getEntity,
  updateEntity,
  createEntity,
  reset
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(DataMasterUpdate);
