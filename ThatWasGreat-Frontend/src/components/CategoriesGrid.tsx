import { useState, useEffect, ChangeEvent } from "react";
import ICategoryData from "../types/Category";
import CategoryService from "../services/CategoryService";

const CategoriesGrid: React.FC = () => {
    const [categories, setCategories] = useState<Array<ICategoryData>>([]);

    useEffect(() => {
        retrieveCategories();
      }, []);
    
    const retrieveCategories = () => {
        CategoryService.getAll()
            .then((response: any) => {
                setCategories(response.data);
                console.log(response.data);
            })
            .catch((e: Error) => {
            console.log(e);
            });
    };

    return (
      <div>
          return <pre>{JSON.stringify(categories, null, ' ')}</pre>;
      </div>
    )
  }

export default CategoriesGrid;
