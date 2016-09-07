package net.hawkengine.http.tests;

import net.hawkengine.core.ServerConfiguration;
import net.hawkengine.http.JobController;
import net.hawkengine.model.Job;
import net.hawkengine.model.ServiceResult;
import net.hawkengine.model.enums.NotificationType;
import net.hawkengine.services.JobService;
import net.hawkengine.services.interfaces.IJobService;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.Application;
import javax.ws.rs.core.Response;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class JobControllerTests extends JerseyTest {
    private JobController jobController;
    private IJobService jobService;
    private Job job;
    private ServiceResult serviceResult;


    @BeforeClass
    public static void setUpClass() {
        ServerConfiguration.configure();
    }

    public Application configure() {
        this.jobService = Mockito.mock(JobService.class);
        this.jobController = new JobController(this.jobService);
        this.serviceResult = new ServiceResult();
        return new ResourceConfig().register(this.jobController);
    }

    @Test
    public void testJobController_constructorTest_notNull() {

        JobController jobController = new JobController();

        assertNotNull(jobController);
    }

    @Test
    public void getAllJobs_nonExistingObjects_emptyList() {
        //Arrange
        List<Job> expectedResult = new ArrayList<>();
        this.serviceResult.setObject(expectedResult);
        Mockito.when(this.jobService.getAll()).thenReturn(this.serviceResult);

        //Act
        Response response = target("/jobs").request().get();
        List<Job> actualResult = response.readEntity(List.class);

        //Assert
        assertEquals(200, response.getStatus());
        assertEquals(expectedResult.size(), actualResult.size());
    }

    @Test
    public void getAllJobs_existingObjects_twoObjects() {
        //Arrange
        List<Job> expectedResult = new ArrayList<>();
        this.job = new Job();
        expectedResult.add(this.job);
        expectedResult.add(this.job);
        this.serviceResult.setObject(expectedResult);
        Mockito.when(this.jobService.getAll()).thenReturn(this.serviceResult);

        //Act
        Response response = target("/jobs").request().get();
        List<Job> actualResult = response.readEntity(List.class);

        //Assert
        assertEquals(200, response.getStatus());
        assertEquals(expectedResult.size(), actualResult.size());
    }

    @Test
    public void getJobById_existingObject_correctObject() {
        //Arrange
        this.job = new Job();
        this.serviceResult.setObject(this.job);
        Mockito.when(this.jobService.getById(Mockito.anyString())).thenReturn(this.serviceResult);
        Job expectedResult = this.job;

        //Act
        Response response = target("/jobs/" + this.job.getId()).request().get();
        Job actualResult = response.readEntity(Job.class);

        //Assert
        assertEquals(200, response.getStatus());
        assertEquals(expectedResult.getId(), actualResult.getId());
    }

    @Test
    public void getJobById_nonExistingObject_properErrorMessage() {
        //Arrange
        String expectedResult = "Job not found.";
        this.serviceResult.setMessage(expectedResult);
        this.serviceResult.setNotificationType(NotificationType.ERROR);
        this.serviceResult.setObject(null);
        Mockito.when(this.jobService.getById(Mockito.any())).thenReturn(this.serviceResult);

        //Act
        Response response = target("/jobs/wrongId").request().get();

        //Assert
        assertEquals(404, response.getStatus());
        assertEquals(expectedResult, response.readEntity(String.class));
    }
}
