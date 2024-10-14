package com.shobhit.projectmanagementsystem.service.impl;

import com.shobhit.projectmanagementsystem.model.Chat;
import com.shobhit.projectmanagementsystem.model.Project;
import com.shobhit.projectmanagementsystem.model.User;
import com.shobhit.projectmanagementsystem.repository.ChatRepository;
import com.shobhit.projectmanagementsystem.repository.ProjectRepository;
import com.shobhit.projectmanagementsystem.repository.UserRepository;
import com.shobhit.projectmanagementsystem.service.ChatService;
import com.shobhit.projectmanagementsystem.service.ProjectService;
import com.shobhit.projectmanagementsystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProjectServiceImpl implements ProjectService {

    @Autowired
    private ProjectRepository projectRepository ;

    @Autowired
    private UserService userService ;

    @Autowired
    private ChatService chatService ;

    @Autowired
    private UserRepository userRepository ;

    @Override
    public Project createProject(Project project, User user) throws Exception {
        Project project1=new Project() ;
        project1.setOwner(user);
        project1.setTags(project.getTags());
        project1.setName(project.getName());
        project1.setCategory(project.getCategory());
        project1.setDescription(project.getDescription());
        project1.getTeam().add(user) ;
        Project savedProject=projectRepository.save(project1) ;
        Chat chat=new Chat() ;
        chat.setProject(savedProject);
        Chat savedChat=chatService.createChat(chat) ;
        savedProject.setChat(savedChat) ;
//        user.setProjectSize(user.getProjectSize()+1);
//        userRepository.save(user) ;
        return savedProject ;
    }

    @Override
    public List<Project> getProjectByTeam(User user, String category, String tag) throws Exception {
        List<Project> projects=projectRepository.findByTeamContainingOrOwner(user,user) ;
        if(category!=null){
            projects=projects.stream().filter(project -> project.getCategory().equals(category))
                    .collect(Collectors.toList());
        }
        if(tag!=null){
            projects=projects.stream().filter(project -> project.getTags().contains(tag))
                    .collect(Collectors.toList());
        }
        return projects;
    }

    @Override
    public Project getProjectById(long projectId) throws Exception {
        Optional<Project> optionalProject=projectRepository.findById(projectId) ;
        if(optionalProject.isEmpty())throw new Exception("project not found") ;
        return optionalProject.get() ;
    }

    @Override
    public void deleteProject(long projectId, long userId) throws Exception {
        boolean exists= projectRepository.existsById(projectId) ;
        if(!exists)throw new Exception("project not found") ;

        projectRepository.deleteById(projectId) ;

    }

    @Override
    public Project updateProject(Project updatedProject, long id) throws Exception {
        Optional<Project> optionalProject=projectRepository.findById(id) ;
        if(optionalProject.isEmpty())throw new Exception("project not found") ;
        Project project=optionalProject.get() ;
        project.setName(updatedProject.getName());
        project.setDescription(updatedProject.getDescription());
        project.setTags(updatedProject.getTags());

        return projectRepository.save(project) ;
    }

    @Override
    public void addUserToProject(long projectId, long userId) throws Exception {
        Optional<Project> optionalProject=projectRepository.findById(projectId) ;
        if(optionalProject.isEmpty())throw new Exception("project not found") ;
        Project project=optionalProject.get() ;

        User user=userService.findUserById(userId) ;

        if(!project.getTeam().contains(user)){
            project.getTeam().add(user) ;
            project.getChat().getUsers().add(user) ;
        }

        projectRepository.save(project) ;

    }

    @Override
    public void removeUserFromProject(long projectId, long userId) throws Exception {
        Optional<Project> optionalProject=projectRepository.findById(projectId) ;
        if(optionalProject.isEmpty())throw new Exception("project not found") ;
        Project project=optionalProject.get() ;

        User user=userService.findUserById(userId) ;

        if(project.getTeam().contains(user)){
            project.getTeam().remove(user) ;
            project.getChat().getUsers().remove(user) ;
        }

        projectRepository.save(project) ;

    }

    @Override
    public Chat getChatByProjectId(long projectId) throws Exception {
        Optional<Project> optionalProject=projectRepository.findById(projectId) ;
        if(optionalProject.isEmpty())throw new Exception("project not found") ;
        Project project=optionalProject.get() ;
        return project.getChat() ;
    }

    @Override
    public List<Project> searchProject(String keyword, User user) throws Exception {
        return projectRepository.findByNameContainingAndTeamContaining(keyword, user) ;
    }


}
