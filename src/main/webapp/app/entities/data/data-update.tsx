import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IClassification } from 'app/shared/model/classification.model';
import { getEntities as getClassifications } from 'app/entities/classification/classification.reducer';
import { IDataMaster } from 'app/shared/model/data-master.model';
import { getEntities as getDataMasters } from 'app/entities/data-master/data-master.reducer';
import { getEntity, updateEntity, createEntity, reset } from './data.reducer';
import { IData } from 'app/shared/model/data.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IDataUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const DataUpdate = (props: IDataUpdateProps) => {
  const [classificationId, setClassificationId] = useState('0');
  const [masterId, setMasterId] = useState('0');
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { dataEntity, classifications, dataMasters, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/data');
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getClassifications();
    props.getDataMasters();
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const entity = {
        ...dataEntity,
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
          <h2 id="jhipsterSampleMongoApp.data.home.createOrEditLabel">
            <Translate contentKey="jhipsterSampleMongoApp.data.home.createOrEditLabel">Create or edit a Data</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : dataEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="data-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="data-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="revisionLabel" for="data-revision">
                  <Translate contentKey="jhipsterSampleMongoApp.data.revision">Revision</Translate>
                </Label>
                <AvField id="data-revision" type="text" name="revision" />
              </AvGroup>
              <AvGroup>
                <Label for="data-classification">
                  <Translate contentKey="jhipsterSampleMongoApp.data.classification">Classification</Translate>
                </Label>
                <AvInput id="data-classification" type="select" className="form-control" name="classificationId">
                  <option value="" key="0" />
                  {classifications
                    ? classifications.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.id}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label for="data-master">
                  <Translate contentKey="jhipsterSampleMongoApp.data.master">Master</Translate>
                </Label>
                <AvInput id="data-master" type="select" className="form-control" name="masterId">
                  <option value="" key="0" />
                  {dataMasters
                    ? dataMasters.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.id}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/data" replace color="info">
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
  classifications: storeState.classification.entities,
  dataMasters: storeState.dataMaster.entities,
  dataEntity: storeState.data.entity,
  loading: storeState.data.loading,
  updating: storeState.data.updating,
  updateSuccess: storeState.data.updateSuccess
});

const mapDispatchToProps = {
  getClassifications,
  getDataMasters,
  getEntity,
  updateEntity,
  createEntity,
  reset
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(DataUpdate);
