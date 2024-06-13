import { Component } from 'react'
import CategoriesGrid from './CategoriesGrid'
import { Button, OverlayTrigger, Tooltip } from 'react-bootstrap'

export default class CategoriesPage extends Component {
  render() {
    return (
      <div className='pageContent'>
        <h1>Categories:</h1>
        <div className='categoryGrid'>
          <CategoriesGrid/>
        </div>
        <OverlayTrigger placement="top" overlay={<Tooltip>New</Tooltip>}>
          <Button variant="outline-secondary" size="lg">+</Button>
        </OverlayTrigger>
      </div>    
    )
  }
}