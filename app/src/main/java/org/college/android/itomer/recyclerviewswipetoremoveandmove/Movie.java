package org.college.android.itomer.recyclerviewswipetoremoveandmove;

/**
 Created by iTomer on 23/12/2015.
 Licence GPLv3
 Copyright (C) 2015 iTomer

 This program is free software: you can redistribute it and/or modify
 it under the terms of the GNU General Public License as published by
 the Free Software Foundation, either version 3 of the License, or
 (at your option) any later version.

 This program is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 GNU General Public License for more details.

 You should have received a copy of the GNU General Public License
 along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
public class Movie {

    //private data members
    private String title;
    private String description;
    private String imageURL;

    //ctor
    public Movie(String title, String description, String imageURL) {
        setTitle(title);
        setDescription(description);
        setImageURL(imageURL);
    }

    //getters And Setters
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getImageURL() {
        return imageURL;
    }
    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }


}
