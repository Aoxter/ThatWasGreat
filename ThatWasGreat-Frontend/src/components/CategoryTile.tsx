import { Link } from 'react-router-dom';
import Category from '../types/Category'
import Card from 'react-bootstrap/Card';

export interface CategoryProps {
    category: Category;
}

export default function CategoryTile(props: CategoryProps) {
  const category = props.category;
    return (
      <div className='categoryTile'>
        <Card>
          <Link to= {{pathname: `/category/${category.id}`}}>
            <Card.Header>{category.name} ({category.id})</Card.Header>
          </Link>
          <Card.Body>
            <Card.Text>{category.description}</Card.Text>
          </Card.Body>
        </Card>
      </div>
  )
}
