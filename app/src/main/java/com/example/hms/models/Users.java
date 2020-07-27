package com.example.hms.models;

public class Users {
        private String fullname;
        private String username;
        private String contact;
        private String email;
        private String password;
    private  String birthyear;
    private  String gender;
    private  String image;
        private boolean expandable;

        public Users(String fullname, String username, String contact, String email, String password) {
            this.fullname = fullname;
            this.username = username;
            this.contact = contact;
            this.email = email;
            this.password = password;
            this.expandable = false;
        }


    public Users(String fullname, String contact, String email, String birthyear, String gender, String image, int update) {
        this.fullname = fullname;
        this.contact = contact;
        this.email = email;
        this.birthyear = birthyear;
        this.gender = gender;
        this.image = image;
    }

    public Users(String fullname, String contact, String email, String birthyear, String gender, int updates) {
        this.fullname = fullname;
        this.contact = contact;
        this.email = email;
        this.birthyear = birthyear;
        this.gender = gender;

    }
        public String getFullname() {
            return fullname;
        }

        public void setFullname(String fullname) {
            this.fullname = fullname;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getContact() {
            return contact;
        }

        public void setContact(String contact) {
            this.contact = contact;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

    public boolean isExpandable() {
        return expandable;
    }

    public void setExpandable(boolean expandable) {
        this.expandable = expandable;
    }

    public String getBirthyear() {
        return birthyear;
    }

    public void setBirthyear(String birthyear) {
        this.birthyear = birthyear;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}

