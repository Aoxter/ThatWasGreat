import { useEffect, useState } from 'react'
import { useParams } from 'react-router-dom';
import CategoryService from "../services/CategoryService";
import Category from '../types/Category';
import { Table } from 'react-bootstrap';
import EntryService from '../services/EntryService';
import Entry from '../types/Entry';

export default function CategoryPage() {
  const { categoryId } = useParams();
  const [category, setCategory] = useState<Category | null>(null);
  const [entries, setEtries] = useState<Entry[]>([]);

  useEffect(() => {
    retrieveCategory();
  }, []);

  const retrieveCategory = () => {
    CategoryService.get(Number(categoryId))
        .then((response: any) => {
            setCategory(response.data);
            console.log("Category loaded");
            console.log(response.data);
            retrievCategoryEntries();
        })
        .catch((e: Error) => {
        console.log(e);
        });
  };

  const retrievCategoryEntries = () => {
    EntryService.getAllInCategory(Number(categoryId))
    .then((response: any) => {
        setEtries(response.data);
        console.log("Entry loaded");
        console.log(response.data);
    })
    .catch((e: Error) => {
    console.log(e);
    });
  }
  
  return (
    category ? (
      <div className='categoryContentContainer'>
        <div className='categoryContent'>
          <h1>{category.name}</h1>
          <p>(id: {category.id})</p>
          <p>{category.description}</p>
          <br/>
          <p className='categoryContentTextLeft'>
            <b>Rating: </b> 
            {category.ratingForm}
          </p>
          <p className='categoryContentTextLeft'>
            <b>Factors:  </b>
            {category.factors.join(", ")}
          </p>
          <Table>
            <thead>
              <tr>
                <th>ID</th> 
                <th>Name</th> 
                <th>Description</th> 
                <th>Rating</th>
              </tr>
            </thead>
            <tbody>
              {entries.map(entry=>{
                return(
                <tr>
                  <td>{entry.id}</td>
                  <td>{entry.name}</td>
                  <td>{entry.description}</td>
                  <td>{entry.overallRate}</td>
                </tr>
                )}
              )}
            </tbody>
          </Table>
        </div>
      </div>
    ) : (
      <div className='pageContent'>
        <p>Category data is not present</p>
      </div>
    )
  )
}