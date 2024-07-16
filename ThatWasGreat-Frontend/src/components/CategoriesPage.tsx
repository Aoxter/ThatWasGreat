import { Component } from 'react'
import CategoriesGrid from './CategoriesGrid'
import { Button, OverlayTrigger, Tooltip } from 'react-bootstrap'
import { Link } from 'react-router-dom'

export default class CategoriesPage extends Component {
  render() {
    return (
      <>
        <h1>Categories:</h1>
        <div className='categoryGrid'>
          <CategoriesGrid/>
        </div>
        <OverlayTrigger placement="top" overlay={<Tooltip>New</Tooltip>}>
          <Link to="/category/new">
            <Button variant="outline-secondary" size="lg">+</Button>
          </Link>
        </OverlayTrigger>
      </>    
    )
  }
}