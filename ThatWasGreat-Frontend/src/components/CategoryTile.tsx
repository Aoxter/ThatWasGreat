import ICategoryData from '../types/Category'
import Card from 'react-bootstrap/Card';

export interface CategoryProps {
    category: ICategoryData;
}

export default function CategoryTile(props: CategoryProps) {
  const category = props.category;
    return (
      <div className='categoryTile'>
        <Card>
          <Card.Header>{category.name}</Card.Header>
          <Card.Body>
            <Card.Text>{category.description}</Card.Text>
          </Card.Body>
        </Card>
      </div>
  )
}
