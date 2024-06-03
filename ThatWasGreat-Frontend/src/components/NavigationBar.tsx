import { Component } from 'react'
import { Link } from 'react-router-dom'
import { FaSignInAlt, FaRegUserCircle  } from "react-icons/fa";

export default class NavigationBar extends Component {
  render() {
    return (
        <div>
            <nav className="navbar navbar-expand-lg navbar-dark bg-dark">
                <a className="navbar-brand" href="/category/all">
                    That Was Great!
                </a>
                <button className="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
                    <span className="navbar-toggler-icon"></span>
                </button>
                <div className="collapse navbar-collapse">
                    <ul className="navbar-nav me-auto">
                        <li className="nav-item">
                            <Link to={"/category/all"} className="nav-link">
                                Categories
                            </Link>
                        </li>
                        <li className="nav-item">
                            <Link to={"/test"} className="nav-link">
                                Test
                            </Link>
                        </li>
                    </ul>
                    <ul className="navbar-nav">
                        <li className="nav-item">
                            <Link to="#" className="nav-link">
                                <FaRegUserCircle />
                                &nbsp;
                                Sign Up
                            </Link>
                        </li>
                        <li className="nav-item">
                            <Link to="#" className="nav-link">
                                <FaSignInAlt/>
                                &nbsp;
                                Login
                            </Link>
                        </li>
                    </ul>
                </div>
            </nav>
        </div>
    )
  }
}
