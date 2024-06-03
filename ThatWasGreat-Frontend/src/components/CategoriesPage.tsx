import { Component } from 'react'
import CategoriesGrid from './CategoriesGrid'

export default class CategoriesPage extends Component {
  render() {
    return (
      <div>
        <h1>Categories:</h1>
        <CategoriesGrid/>
      </div>    
    )
  }
}