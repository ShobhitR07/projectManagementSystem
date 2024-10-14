package com.shobhit.projectmanagementsystem.service;

import com.shobhit.projectmanagementsystem.model.Chat;
import com.shobhit.projectmanagementsystem.model.Project;
import com.shobhit.projectmanagementsystem.model.User;

import java.util.List;

public interface ProjectService {
    Project createProject(Project project, User user) throws Exception ;
    List<Project> getProjectByTeam(User user, String category, String tag) throws Exception ;
    Project getProjectById(long projectId) throws Exception ;
    void deleteProject(long projectId, long userId) throws Exception ;
    Project updateProject(Project updatedProject, long id) throws Exception ;
    void addUserToProject(long projectId, long userId) throws Exception ;
    void removeUserFromProject(long projectId, long userId) throws Exception ;
    Chat getChatByProjectId(long projectId) throws Exception ;
    List<Project> searchProject(String keyword,User user) throws Exception ;


}
