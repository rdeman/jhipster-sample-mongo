import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { Translate, ICrudGetAllAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './data.reducer';
import { IData } from 'app/shared/model/data.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IDataProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export const Data = (props: IDataProps) => {
  useEffect(() => {
    props.getEntities();
  }, []);

  const { dataList, match, loading } = props;
  return (
    <div>
      <h2 id="data-heading">
        <Translate contentKey="jhipsterSampleMongoApp.data.home.title">Data</Translate>
        <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
          <FontAwesomeIcon icon="plus" />
          &nbsp;
          <Translate contentKey="jhipsterSampleMongoApp.data.home.createLabel">Create new Data</Translate>
        </Link>
      </h2>
      <div className="table-responsive">
        {dataList && dataList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="global.field.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="jhipsterSampleMongoApp.data.revision">Revision</Translate>
                </th>
                <th>
                  <Translate contentKey="jhipsterSampleMongoApp.data.classification">Classification</Translate>
                </th>
                <th>
                  <Translate contentKey="jhipsterSampleMongoApp.data.master">Master</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {dataList.map((data, i) => (
                <tr key={`entity-${i}`}>
                  <td>
                    <Button tag={Link} to={`${match.url}/${data.id}`} color="link" size="sm">
                      {data.id}
                    </Button>
                  </td>
                  <td>{data.revision}</td>
                  <td>
                    {data.classificationId ? <Link to={`classification/${data.classificationId}`}>{data.classificationId}</Link> : ''}
                  </td>
                  <td>{data.masterId ? <Link to={`data-master/${data.masterId}`}>{data.masterId}</Link> : ''}</td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${data.id}`} color="info" size="sm">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${data.id}/edit`} color="primary" size="sm">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${data.id}/delete`} color="danger" size="sm">
                        <FontAwesomeIcon icon="trash" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.delete">Delete</Translate>
                        </span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        ) : (
          !loading && (
            <div className="alert alert-warning">
              <Translate contentKey="jhipsterSampleMongoApp.data.home.notFound">No Data found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

const mapStateToProps = ({ data }: IRootState) => ({
  dataList: data.entities,
  loading: data.loading
});

const mapDispatchToProps = {
  getEntities
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(Data);
